package ua.hudyma.domain.certify;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AircraftType {
    A322(2, 6),         // Airbus A321XLR: 2 пілоти, до 6 бортпровідників (довгі рейси)
    AN_148(2, 4),       // Ан-148: 2 пілоти, до 4 бортпровідників
    BOEING_739ER(2, 6), // Boeing 737-900ER: 2 пілоти, 4–6 бортпровідників (залежно від конфігурації)
    BOEING_763ER(3, 10),// Boeing 767-300ER: 2–3 пілоти (ULR), до 10 бортпровідників
    SAAB_340A(2, 2),    // Saab 340A: 2 пілоти, 1–2 бортпровідники
    EMBRAER_E190(2, 4); // Embraer E190: 2 пілоти, до 4 бортпровідників
    private final int maxPilots;
    private final int maxCabinCrew;

    public int getMaxPilots() {
        return maxPilots;
    }
    public int getMaxCabinCrew() {
        return maxCabinCrew;
    }
}


