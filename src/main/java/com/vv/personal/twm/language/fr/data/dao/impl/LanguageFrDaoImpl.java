package com.vv.personal.twm.language.fr.data.dao.impl;

import com.vv.personal.twm.language.fr.data.dao.LanguageFrDao;
import com.vv.personal.twm.language.fr.data.entity.LanguageFrEntity;
import com.vv.personal.twm.language.fr.data.repository.LanguageFrRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class LanguageFrDaoImpl implements LanguageFrDao {

  private final LanguageFrRepository languageFrRepository;

  @Override
  public void addWord(LanguageFrEntity frenchWord) {
    languageFrRepository.saveAndFlush(frenchWord);
  }

  @Override
  public void updateWord(LanguageFrEntity frenchWord) {
    Optional<LanguageFrEntity> languageFrEntityOptional = getWord(frenchWord.getFrenchWord());
    if (languageFrEntityOptional.isPresent()) {
      LanguageFrEntity languageFrEntity = languageFrEntityOptional.get();
      languageFrEntity.setEnglishMeaning(frenchWord.getEnglishMeaning());
      languageFrEntity.setPronunciation(frenchWord.getPronunciation());
    }
    languageFrRepository.saveAndFlush(frenchWord);
  }

  @Override
  public boolean doesWordExist(String frenchWord) {
    return languageFrRepository.existsByFrenchWord(frenchWord);
  }

  @Override
  public Optional<LanguageFrEntity> getWord(String frenchWord) {
    return languageFrRepository.findByFrenchWord(frenchWord);
  }

  @Override
  public List<LanguageFrEntity> getAllWords() {
    return languageFrRepository.findAll();
  }

  @Override
  public List<LanguageFrEntity> getFrenchWordsContainingWordInMeaning(String englishWord) {
    return languageFrRepository.findFrenchWordsContainingWordInMeaning(englishWord);
  }

  @Override
  public List<LanguageFrEntity> getFrenchWordsMatchingPos(String posTag) {
    return languageFrRepository.findFrenchWordsMatchingPos(posTag);
  }

  @Override
  public List<LanguageFrEntity> getFrenchWordsMatchingGender(String gender) {
    return languageFrRepository.findFrenchWordsMatchingGender(gender);
  }
}
