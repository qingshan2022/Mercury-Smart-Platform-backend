package com.mercury.blog.model.dto.notification;

import lombok.Data;
import com.mercury.common.utils.PageRequest;

import java.io.Serializable;

@Data
public class NotificationQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
}
