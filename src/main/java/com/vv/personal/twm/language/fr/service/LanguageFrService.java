package com.vv.personal.twm.language.fr.service;

import com.vv.personal.twm.language.fr.model.LanguageFr;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-05-17
 */
public interface LanguageFrService {

  void addWord(LanguageFr frenchWord) throws InvalidObjectException;

  void updateWord(LanguageFr frenchWord);

  boolean doesWordExist(String frenchWord);

  Optional<LanguageFr> getFrenchWordDetail(String frenchWord);

  List<LanguageFr> getAllFrenchWords();

  List<LanguageFr> getFrenchWordsContainingWord(String englishWord);

  List<LanguageFr> getFrenchWordsMatchingPos(String posTag);

  List<LanguageFr> getFrenchWordsMatchingGender(String gender);

  int bulkUpload(String fileLocation, String source);
}
