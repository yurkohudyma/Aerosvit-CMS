package ua.hudyma.domain.visa;

public enum VisaStatus {
    ACTIVE,        // діюча
    EXPIRED,       // термін дії закінчився
    REVOKED,       // анульована
    CANCELLED,     // скасована заявником або держорганом
    PENDING        // ще не видана або в процесі розгляду
}
