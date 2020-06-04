package com.back.admin.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_STUDENT","학생(멘티)"),
    HOST("ROLE_MENTOR","멘토");
    private final String key;
    private final String title;
}
