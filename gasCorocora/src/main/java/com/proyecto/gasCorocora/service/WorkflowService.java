package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WorkflowService {

    private static final String PROCESS_DEFINITION_KEY = "Process_0lrnoqy";

    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public WorkflowService(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public Map<String, Object> startProcess(Map<String, Object> variables) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, safeVariables(variables));
        completeCurrentTask(instance.getProcessInstanceId(), variables);
        return describe(instance.getProcessInstanceId());
    }

    public Map<String, Object> completeCurrentTask(String processInstanceId, Map<String, Object> variables) {
        Map<String, Object> mergedVariables = new HashMap<>(getVariables(processInstanceId));
        mergedVariables.putAll(safeVariables(variables));

        Task task = findCurrentTaskWithRetry(processInstanceId);
        if (task != null) {
            taskService.complete(task.getId(), mergedVariables);
        }
        return describe(processInstanceId);
    }

    public Map<String, Object> describe(String processInstanceId) {
        Map<String, Object> state = new HashMap<>();
        state.put("processDefinitionKey", PROCESS_DEFINITION_KEY);
        state.put("processInstanceId", processInstanceId);
        state.put("variables", getVariables(processInstanceId));

        Task task = findCurrentTaskWithRetry(processInstanceId);
        if (task != null) {
            state.put("currentTaskId", task.getId());
            state.put("currentTaskName", task.getName());
            state.put("state", "RUNNING");
        } else {
            state.put("currentTaskId", null);
            state.put("currentTaskName", null);
            state.put("state", "FINISHED");
        }
        return state;
    }

    public Map<String, Object> getVariables(String processInstanceId) {
        try {
            return new HashMap<>(runtimeService.getVariables(processInstanceId));
        } catch (Exception ignored) {
            return new HashMap<>();
        }
    }

    public Optional<Task> findCurrentTask(String processInstanceId) {
        return Optional.ofNullable(taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .active()
            .singleResult());
    }

    private Task findCurrentTaskWithRetry(String processInstanceId) {
        for (int attempt = 0; attempt < 10; attempt++) {
            Task task = findCurrentTask(processInstanceId).orElse(null);
            if (task != null) {
                return task;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return findCurrentTask(processInstanceId).orElse(null);
    }

    private Map<String, Object> safeVariables(Map<String, Object> variables) {
        return variables == null ? new HashMap<>() : new HashMap<>(variables);
    }
}
