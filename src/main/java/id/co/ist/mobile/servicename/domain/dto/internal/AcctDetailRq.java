package id.co.ist.mobile.servicename.domain.dto.internal;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcctDetailRq {

    @ApiModelProperty(example = "703061920300")
    private String accountNumber;
    @ApiModelProperty(example = "SDA")
    private String accountType;

}