package com.vv.personal.twm.language.fr.data.dao;

import com.vv.personal.twm.language.fr.data.entity.LanguageFrEntity;
import java.util.List;
import java.util.Optional;

/**
 * @author Vivek
 * @since 2025-05-17
 */
public interface LanguageFrDao {

  void addWord(LanguageFrEntity frenchWord);

  void updateWord(LanguageFrEntity frenchWord);

  boolean doesWordExist(String frenchWord);

  Optional<LanguageFrEntity> getWord(String frenchWord);

  List<LanguageFrEntity> getAllWords();

  List<LanguageFrEntity> getFrenchWordsContainingWordInMeaning(String englishWord);

  List<LanguageFrEntity> getFrenchWordsMatchingPos(String posTag);

  List<LanguageFrEntity> getFrenchWordsMatchingGender(String gender);
}
