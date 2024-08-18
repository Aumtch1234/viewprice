package com.ViewPrice.modal;

import com.ViewPrice.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnble = false;
    private VerificationType sendTo;
}
