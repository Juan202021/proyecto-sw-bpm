package com.example.runner.web;

import com.example.runner.service.WorkflowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/start")
    public Map<String, Object> start(@RequestBody(required = false) Map<String, Object> variables) {
        return workflowService.startProcess(variables);
    }

    @GetMapping("/{processInstanceId}")
    public Map<String, Object> describe(@PathVariable String processInstanceId) {
        return workflowService.describe(processInstanceId);
    }

    @GetMapping("/{processInstanceId}/task")
    public Map<String, Object> currentTask(@PathVariable String processInstanceId) {
        return workflowService.describe(processInstanceId);
    }

    @GetMapping("/{processInstanceId}/variables")
    public Map<String, Object> variables(@PathVariable String processInstanceId) {
        return workflowService.getVariables(processInstanceId);
    }

    @PostMapping("/{processInstanceId}/complete")
    public Map<String, Object> complete(@PathVariable String processInstanceId,
                                        @RequestBody(required = false) Map<String, Object> variables) {
        return workflowService.completeCurrentTask(processInstanceId, variables);
    }
}
