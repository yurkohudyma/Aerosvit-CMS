package ua.hudyma.domain.profile;

public enum PilotType {
    CPT,        // Captain – Капітан (командир повітряного судна)
    SFO,        // Senior First Officer – Старший другий пілот
    FO,         // First Officer – Другий пілот
    SO,         // Second Officer – Молодший другий пілот / технічний пілот
    RO,         // Relief Officer – Пілот, який змінює основний екіпаж на довгих рейсах (пілот відпочинку)
    TRI,        // Type Rating Instructor – Інструктор із типу ПС (навчає керуванню конкретним типом літака)
    TRE,        // Type Rating Examiner – Екзаменатор із типу ПС (приймає іспити на тип)
    LTC,        // Line Training Captain – Капітан, що проводить лінійне навчання
    LTC_FO,     // Line Training First Officer – Другий пілот, що навчає на лінії (рідко використовується, зазвичай для навчання FO)
    DE,         // Deputy Examiner / Designated Examiner – Заступник або уповноважений екзаменатор
    OBS,        // Observer – Спостерігач (не виконує політ, спостерігає за екіпажем)
    SA,         // Safety Auditor / Safety Analyst – Спеціаліст із безпеки (може проводити аудит з безпеки)
    TRAINEE     // Trainee – Стажер, який ще навчається
}

