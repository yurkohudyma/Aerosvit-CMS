package ua.hudyma.domain.training.enums;

public enum TrainingType {
    SIMULATOR,             // Повнофункціональний симулятор (FFS, FTD)
    CBT,                   // Computer-Based Training
    CLASSROOM,             // Аудиторне навчання (теорія, інструктажі)
    LINE_TRAINING,         // Лінійний тренінг (у реальному польоті)
    LOFT,                  // Line-Oriented Flight Training (сценарії)
    UPRT,                  // Upset Prevention and Recovery Training
    CRM,                   // Crew Resource Management
    EMERGENCY_PROCEDURES,  // Надзвичайні процедури (евакуація, пожежа тощо)
    RECURRENT,             // Періодичний тренінг (щорічний/кожні 6 міс)
    TYPE_RATING            // Типова кваліфікація (на конкретний тип літака)
}

