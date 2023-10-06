package dev.uberlan.siscacs.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface MunicaoRepository extends JpaRepository<Municao, UUID> {
}
