package com.equadis.bank.core.utils;

import com.equadis.bank.core.model.audit.Audit;
import lombok.experimental.UtilityClass;

import java.time.OffsetDateTime;

@UtilityClass
public class AuditManager {

    public static Audit created() {
        return Audit.builder()
                .createdAt(OffsetDateTime.now())
                .build();
    }

    public static Audit updated(Audit audit) {
        return audit.toBuilder()
                .createdAt(audit.getCreatedAt())
                .updateAd(OffsetDateTime.now())
                .build();
    }
}
