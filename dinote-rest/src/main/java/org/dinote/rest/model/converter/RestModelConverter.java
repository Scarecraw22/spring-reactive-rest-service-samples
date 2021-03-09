package org.dinote.rest.model.converter;

import org.dinote.rest.model.request.DinoteRestRequest;
import org.dinote.rest.model.response.DinoteRestResponse;

public interface RestModelConverter<S extends DinoteRestRequest, E, D extends DinoteRestResponse> {

    E fromRequest(S request);

    D toResponse(E model);
}
