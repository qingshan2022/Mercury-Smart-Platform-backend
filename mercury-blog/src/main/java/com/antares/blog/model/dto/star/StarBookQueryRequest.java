package com.mercury.blog.model.dto.star;

import lombok.Data;
import com.mercury.common.utils.PageRequest;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class StarBookQueryRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = 2222903543143705835L;

    private Long bookId;
}
