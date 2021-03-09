package org.dinote.rest.user.add.model.response;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.dinote.rest.model.response.DinoteRestResponse;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class AddUserResponse implements DinoteRestResponse {

    Long id;
    String name;
    String email;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
