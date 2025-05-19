package com.vv.personal.twm.language.fr.model;

import java.util.List;
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
  private List<String> posTags;
  private String pronunciation;
  private Character gender2;
  private String source;
}
