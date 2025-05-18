package com.vv.personal.twm.language.fr.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "language_fr")
public class LanguageFrEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  @Column(name = "fr")
  private String frenchWord;

  @Column(name = "en")
  private String englishMeaning;

  @Column(name = "gender")
  private String gender;

  @Column(name = "pos")
  private String posTag;

  @Column(name = "pronunciation")
  private String pronunciation;

  @Column(name = "gender2")
  private Character gender2;

  @Column(name = "cre_ts")
  private Instant createTimestamp;

  @Column(name = "upd_ts")
  private Instant updateTimestamp;
}
