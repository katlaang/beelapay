package com.beelapay.audit.service;

import com.beelapay.audit.domain.AuditEvent;
import com.beelapay.audit.repo.AuditEventRepository;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditEventRepository auditEventRepository;

    public AuditService(AuditEventRepository auditEventRepository) {
        this.auditEventRepository = auditEventRepository;
    }

    public void record(String eventType, UUID actorId, String referenceId, String context) {
        AuditEvent event = new AuditEvent();
        event.setId(UUID.randomUUID());
        event.setEventType(eventType);
        event.setActorId(actorId);
        event.setReferenceId(referenceId);
        event.setContext(context);
        event.setCreatedAt(Instant.now());
        auditEventRepository.save(event);
    }
}
