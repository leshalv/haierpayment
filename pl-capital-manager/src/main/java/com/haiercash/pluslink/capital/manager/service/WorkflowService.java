package com.haiercash.pluslink.capital.manager.service;

//import com.haiercash.pluslink.capital.common.enums.ReturnCode;

import com.haiercash.pluslink.capital.common.exception.PlCapitalException;
import com.haiercash.pluslink.capital.common.ribbon.BaseRestClient;
import com.haiercash.pluslink.capital.enums.ReturnCode;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

//import com.haiercash.pluslink.capital.ribbon.BaseRestClient;
//import org.json.JSONArray;
//import org.json.JSONObject;

/**
 * @author zhanggaowei
 * @date 2018/5/29
 */
@Slf4j
@Service
@ConfigurationProperties(prefix = "app.workflow.info")
public class WorkflowService extends BaseRestClient {

    @Autowired
    private PortalService portalService;
    /**
     * 工作流引擎rest接口根地址
     */
    private String workflowRest;

    private static Map<String, String> workflowMap = new HashMap<>();

    private static List<String> workflowList;


    static {

        workflowMap.put("importApply", "ROLE_DIRECTOR");
        workflowMap.put("DirectorConfirm", "");

        workflowList = Arrays.asList("importApply");
    }

    /**
     * 启动工作流
     *
     * @param workflowKey 工作流引擎key
     * @param businessKey 业务数据唯一标识
     * @param callback    审批状态更新回调地址
     * @param user        启动工作流的用户
     * @return 成功时返回工作流Id，失败返回""
     */
    public String startWorkflow(String workflowKey, String businessKey, String callback, String user) throws Exception {
        JSONObject joVar = new JSONObject();
        JSONObject joPar = new JSONObject();
        joPar.put("value", callback);
        joPar.put("type", "string");
        joVar.put("callback", joPar);

        joPar = new JSONObject();
        joPar.put("value", businessKey);
        joPar.put("type", "string");
        joVar.put("businessKey", joPar);

        joPar = new JSONObject();
        joPar.put("value", String.format("|%s|", user));
        joPar.put("type", "string");
        joVar.put("nextUsers", joPar);

        joPar = new JSONObject();
        joPar.put("value", user);
        joPar.put("type", "string");
        joVar.put("startUser", joPar);
        joVar.put("opUser", joPar);

        joPar = new JSONObject();
        joPar.put("value", workflowKey);
        joPar.put("type", "string");
        joVar.put("workflowType", joPar);

        JSONObject jo = new JSONObject();
        jo.put("businessKey", businessKey);
        jo.put("variables", joVar);

        String url = String.format("/process-definition/key/%s/start", workflowKey);
        String json = this.postForEntity(workflowRest + url, jo.toString(), String.class);
        if (json == null || json.isEmpty()) {
            log.error("开启工作流失败");
            throw new PlCapitalException(ReturnCode.msg_workflow_fail, "开启工作流失败无法审批");
        } else {
            //return ReturnCode.success.getCode()+json;
            return "";
        }
    }

    /**
     * 获取当前未关闭的工作流ID
     *
     * @param businessKey
     * @return 工作流ID
     */
    public String getActiveWorkflow(String businessKey) throws Exception {
        String url = String.format("/process-instance?businessKey=%s", businessKey);
        String json = this.restGet(workflowRest + url);
        if (json.equals("[]")) {
            String url1 = String.format("/history/process-instance?processInstanceBusinessKey=%s", businessKey);
            String json1 = this.restGet(workflowRest + url1);
            JSONArray js = new JSONArray(json1);
            return getJsonString(js.getJSONObject(0), "id");
        } else {
            JSONArray js = new JSONArray(json);
            return getJsonString(js.getJSONObject(0), "id");
        }
    }

    /**
     * 查询指定业务的审批记录
     *
     * @param businessKey
     * @param page        页号
     * @param size        每页行数
     * @return json array
     */
    public Map<String, Object> queryApprovalList(String businessKey, int page, int size) throws Exception {
        String procId = this.getActiveWorkflow(businessKey);
        String url = String.format("/history/task?processInstanceId=%s&firstResult=%d&maxResults=%d", procId, page, size);
        String json = this.restGet(workflowRest + url);
        if (json != null) {
            String countJson = this.restGet(workflowRest + String.format("/history/task/count?processInstanceId=%s", procId));
            Integer count = Integer.parseInt(getJsonString(countJson, "count"));
            List<Map<String, Object>> list = new ArrayList<>();
            if (json.startsWith("[")) {
                JSONArray ja = new JSONArray(json);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", getJsonString(jo, "id"));
                    m.put("name", getJsonString(jo, "name"));
                    String des = getJsonString(jo, "description");
                    String[] arr = des.split("\\|\\|");
                    if (arr.length > 2) {
                        m.put("description", arr[2]);
                    } else {
                        m.put("description", "");
                    }
                    String assignee = getJsonString(jo, "assignee");
                    if (arr.length > 3) {
                        m.put("result", arr[3]);
                    } else {
                        m.put("result", "待审批");
                        String[] assigneeArray = assignee.split("\\|");
                        assignee = "";
                        for (int n = 0; n < assigneeArray.length; n++) {
                            if (!assigneeArray[n].equals("")) {
                                assignee += "," + assigneeArray[n];
                            }
                        }
                        if (!assignee.isEmpty()) {
                            assignee = assignee.substring(1);
                        }
                    }
                    m.put("assignee", assignee);
                    m.put("startTime", getJsonString(jo, "startTime").replaceAll("T", " "));
                    m.put("endTime", getJsonString(jo, "endTime").replaceAll("T", " "));
                    list.add(m);
                }
            }
            Map<String, Object> resultMap = new LinkedHashMap<>();
            resultMap.put("data", list);
            resultMap.put("count", count);
            return resultMap;
        }
        return null;
    }

    /**
     * 判断用户是否允许审核操作
     *
     * @param workflowKey
     * @param businessKey 审批流ID
     * @param userName    用户名
     * @return
     */
    public boolean approveAuthority(String workflowKey, String businessKey, String userName) throws Exception {
        String jsonInfo = this.restGet(workflowRest + String.format("/task?processDefinitionKey=%s&processInstanceBusinessKey=%s", workflowKey, businessKey));
        log.info("工作流返回报文结果为：{}", jsonInfo);
        if (workflowList.contains(getJsonString(jsonInfo, "taskDefinitionKey")))
            return userName.equals(this.getStartUser(this.getActiveWorkflow(businessKey)));
        else {
            String url = String.format("/task/count?assigneeLike=%%|%s|%%&processInstanceId=%s", userName, this.getActiveWorkflow(businessKey));
            String json = this.restGet(workflowRest + url);
            return Integer.parseInt(getJsonString(json, "count")) > 0;
        }
    }

    /**
     * 审批流程
     *
     * @param workflowKey 工作流引擎key
     * @param businessKey
     * @param reason      审批原因
     * @param opUser      当前操作的用户，写入task的assignee字段
     * @param isOk        通过or驳回
     * @return 是否审批成功
     */
    public String approveExecute(String workflowKey, String businessKey, String reason, String opUser, boolean isOk) throws Exception {
        String proId = this.getActiveWorkflow(businessKey);
        Future<String> future = CompletableFuture.supplyAsync(() -> isOk ? this.getNextStageUsers(workflowKey, businessKey) : this.getStartUser(proId));
        String taskInfo = this.restGet(workflowRest + String.format("/task?processInstanceId=%s", proId));
        JSONArray jaTask = new JSONArray(taskInfo);
        JSONObject joTask = jaTask.getJSONObject(0);
        String taskId = joTask.getString("id");
        joTask.put("description", String.format("%s||%s||%s", joTask.getString("description"), reason, isOk ? "通过" : "驳回"));
        joTask.put("assignee", opUser);
        try {
            String nextUsers = future.get();
            if (nextUsers == null) {
                return ReturnCode.msg_approve_role_not_exists.getCode();
            }
            Future<Void> future1 = CompletableFuture.runAsync(() -> {
                String data = String.format("{ \"value\": \"%s\", \"type\": \"string\" }", opUser);
                this.restPut(workflowRest + String.format("/process-instance/%s/variables/opUser", proId), data, 204);
                if (!"".equals(nextUsers)) {
                    data = String.format("{ \"value\": \"%s\", \"type\": \"string\" }", nextUsers);
                    this.restPut(workflowRest + String.format("/process-instance/%s/variables/nextUsers", proId), data, 204);
                }
            });
            this.restPut(workflowRest + String.format("/task/%s", taskId), joTask.toString(), 204);
            future1.get();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        String data = String.format("{ \"variables\": { \"approved\": { \"value\": \"%s\", \"type\": \"boolean\" } } }",
                isOk ? "true" : "false");
        return this.restPost(workflowRest + String.format("/task/%s/complete", taskId), data, 204);
    }

    /**
     * 获取下一个节点的审批用户
     *
     * @param businessKey 业务数据唯一标识
     * @return 审批角色
     */
    public String getNextStageUsers(String workflowKey, String businessKey) {
        String json = this.restGet(workflowRest + String.format("/task?processDefinitionKey=%s&processInstanceBusinessKey=%s", workflowKey, businessKey));
        String roleAudit = workflowMap.get(getJsonString(json, "taskDefinitionKey"));
        if ("".equals(roleAudit)) {
            return "";
        }
        return portalService.getUsers(roleAudit);
    }

    public String getStartUser(String procId) {
        try {
            String url = String.format("/process-instance/%s/variables/startUser", procId);
            String json = this.restGet(workflowRest + url);
            return getJsonString(new JSONObject(json), "value");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private String getJsonString(String json, String key) {
        try {
            JSONObject jo;
            if (json.startsWith("[")) {
                JSONArray ja = new JSONArray(json);
                jo = ja.getJSONObject(0);
            } else {
                jo = new JSONObject(json);
            }

            return getJsonString(jo, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    private String getJsonString(JSONObject jo, String key) throws Exception {
        Object value = jo.get(key);
        if (value == JSONObject.NULL) {
            return "";
        } else {
            return value.toString();
        }
    }

    public String getWorkflowRest() {
        return workflowRest;
    }

    public void setWorkflowRest(String workflowRest) {
        this.workflowRest = workflowRest;
    }
}
