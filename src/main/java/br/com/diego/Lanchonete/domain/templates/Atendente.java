package br.com.diego.Lanchonete.domain.templates;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true, callSuper=false)
@JsonRootName(value = "attendants")
@Table(name = "ATENDENTES")
@Entity
public class Atendente extends Pessoa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "O campo 'dailyGoal' não pode ser null")
    @Column(length = 30, name="META_DIARIA") @JsonProperty("dailyGoal")
    private int metaDiaria;
}