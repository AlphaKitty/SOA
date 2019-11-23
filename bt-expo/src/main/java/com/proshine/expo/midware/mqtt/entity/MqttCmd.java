package com.proshine.expo.midware.mqtt.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.proshine.expo.base.HttpClientUtil;
import com.proshine.expo.base.Identities;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumParamKey;
import com.proshine.expo.midware.mqtt.entity.enumer.EnumService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前后台消息通信指令
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqttCmd {
    /**
     * 消息ID, 唯一标示符
     */
    private String id = "";
    /**
     * 服务类型
     * 服务类型参考 {@link EnumService}
     */
    private String service = "";
    /**
     * 操作类型
     * 操作类型参考 {@link EnumService.EnumProgramOps} {@link EnumService.EnumTerminalOps}
     */
    private String operation = "";
    /**
     * 携带的参数列表
     * Key参考 {@link EnumParamKey}
     */
    @SuppressWarnings("rawtypes")
    private Map params = null;

    /**
     * 快捷创建MqttCmd实例
     *
     * @param service   {@link EnumService}
     * @param operation <p> {@link EnumService.EnumProgramOps}
     *                  or {@link EnumService.EnumTerminalOps} </p>
     * @return MqttCmd 实例
     */
    public static MqttCmd create(String service, String operation) {
        return new MqttCmd(Identities.uuid2(), service, operation, null);
    }

    /**
     * 快捷创建MqttCmd实例
     *
     * @param service   {@link EnumService} 服务类型
     * @param operation <p> {@link EnumService.EnumProgramOps}
     *                  or {@link EnumService.EnumTerminalOps} 操作类型 </p>
     * @param params    {@link Map} 参数列表
     * @return MqttCmd 实例
     */
    @SuppressWarnings("rawtypes")
    public static MqttCmd create(String service, String operation, Map params) {
        return new MqttCmd(Identities.uuid2(), service, operation, params);
    }

    /**
     * Emqtt 作为消息服务器时, 需要该接口用于获取在线设备
     */
    @Deprecated
    public static List<String> getAllClient() {
        String result = HttpClientUtil.doGet(MqttConstant.EMQ_CLIENTS_API, null, "UTF-8", true);
        JSONObject json = (JSONObject) JSONObject.parse(result);
        JSONArray jsonArray = (JSONArray) JSONArray.parse(json.get("result").toString());
        List<String> terminalIds = new ArrayList<>();
        for (Object jsonObject : jsonArray) {
            JSONObject object = (JSONObject) jsonObject;
            String clientId = object.getString("clientId");
            terminalIds.add(clientId);
        }
        return terminalIds;
    }

}

