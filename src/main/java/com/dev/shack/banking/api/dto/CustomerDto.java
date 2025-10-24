package com.dev.shack.banking.api.dto;

import java.util.List;

public record CustomerDto(Long id,
                          String name,
                          String email,
                          List<AccountDto> accounts) {
}
