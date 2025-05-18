package com.vv.personal.twm.language.fr.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Data
@Builder
public class LanguageFr {

  private String frenchWord;
  private String englishMeaning;
  private String gender;
  private String posTag;
  private String pronunciation;
  private Character gender2;
}
