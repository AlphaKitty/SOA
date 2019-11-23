package com.proshine.expo.terminal.terminal.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "touchsys_configuration_programschedule")
public class ProgramSchedule {

    @Id // 主键类型只能为：String，ObjectId,BigInteger
    @Indexed
    private String id;

    private List<Map<String, String>> schedules;

    private String version;

    private String programId;

    private String cstmId;
}
