package cnu.healthcare.domain.group.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinGroupDto {
    private String groupCode;
    private String memberId;
}
