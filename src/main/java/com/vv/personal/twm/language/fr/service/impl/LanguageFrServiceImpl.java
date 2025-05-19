package com.vv.personal.twm.language.fr.service.impl;

import com.google.common.collect.Lists;
import com.vv.personal.twm.language.fr.data.dao.LanguageFrDao;
import com.vv.personal.twm.language.fr.data.entity.LanguageFrEntity;
import com.vv.personal.twm.language.fr.model.LanguageFr;
import com.vv.personal.twm.language.fr.service.LanguageFrService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InvalidObjectException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class LanguageFrServiceImpl implements LanguageFrService {

  private final LanguageFrDao languageFrDao;

  @Override
  public void addWord(LanguageFr frenchWord) throws InvalidObjectException {
    Instant currentTime = Instant.now();
    LanguageFrEntity entity = generateLanguageFrEntity(frenchWord, currentTime);
    languageFrDao.addWord(entity);
  }

  @Override
  public void updateWord(LanguageFr frenchWord) {
    LanguageFrEntity entity = generateLanguageFrEntity(frenchWord);
    languageFrDao.updateWord(entity);
  }

  @Override
  public boolean doesWordExist(String frenchWord) {
    return languageFrDao.doesWordExist(frenchWord);
  }

  @Override
  public Optional<LanguageFr> getFrenchWordDetail(String frenchWord) {
    return languageFrDao.getWord(frenchWord).map(this::generateLanguageFr);
  }

  @Override
  public List<LanguageFr> getAllFrenchWords() {
    return languageFrDao.getAllWords().stream()
        .map(this::generateLanguageFr)
        .collect(Collectors.toList());
  }

  @Override
  public List<LanguageFr> getFrenchWordsContainingWord(String englishWord) {
    return languageFrDao.getFrenchWordsContainingWordInMeaning(englishWord).stream()
        .map(this::generateLanguageFr)
        .collect(Collectors.toList());
  }

  @Override
  public List<LanguageFr> getFrenchWordsMatchingPos(String posTag) {
    return languageFrDao.getFrenchWordsMatchingPos(posTag).stream()
        .map(this::generateLanguageFr)
        .collect(Collectors.toList());
  }

  @Override
  public List<LanguageFr> getFrenchWordsMatchingGender(String gender) {
    return languageFrDao.getFrenchWordsMatchingGender(gender).stream()
        .map(this::generateLanguageFr)
        .collect(Collectors.toList());
  }

  @Override
  public void bulkUpload(String fileLocation) {
    List<LanguageFr> frenchWords = Lists.newArrayList();

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileLocation))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        line = line.strip().toLowerCase();
        if (line.contains("gender2")) continue; // header line

        try {
          String[] parts = line.split("\t");
          String frenchWord = sanitizeString(parts[0]);
          String englishMeaning = sanitizeString(parts[1]);
          String gender = sanitizeString(parts[2]);
          Character gender2 = sanitizeString(parts[3]).charAt(0);
          String pronunciation = sanitizeString(parts[4]);
          String posTag = sanitizeString(parts[5]);
          List<String> posTags =
              Arrays.stream(posTag.split(",")).map(String::strip).collect(Collectors.toList());

          LanguageFr word =
              LanguageFr.builder()
                  .frenchWord(frenchWord)
                  .englishMeaning(englishMeaning)
                  .gender(gender)
                  .gender2(gender2)
                  .pronunciation(pronunciation)
                  .posTags(posTags)
                  .build();
          frenchWords.add(word);
        } catch (Exception e) {
          log.error("Parsing error of line '{}'", line, e);
        }
      }
    } catch (Exception e) {
      log.error("Failed to read file {}", fileLocation, e);
    }

    frenchWords.forEach(
        word -> {
          if (!doesWordExist(word.getFrenchWord())) {
            try {
              addWord(word);
            } catch (Exception e) {
              log.error("Error while uploading {}", word, e);
            }
          } else {
            log.info("Skipping {} as it is already present", word.getFrenchWord());
          }
        });
  }

  private LanguageFr generateLanguageFr(LanguageFrEntity languageFrEntity) {
    List<String> posTags =
        Arrays.stream(StringUtils.split(languageFrEntity.getPosTag(), ','))
            .map(String::strip)
            .toList();

    return LanguageFr.builder()
        .frenchWord(languageFrEntity.getFrenchWord())
        .englishMeaning(languageFrEntity.getEnglishMeaning())
        .gender(languageFrEntity.getGender())
        .posTags(posTags)
        .pronunciation(languageFrEntity.getPronunciation())
        .gender2(languageFrEntity.getGender2())
        .build();
  }

  private LanguageFrEntity generateLanguageFrEntity(LanguageFr languageFr, Instant currentTs)
      throws InvalidObjectException {
    if (StringUtils.isEmpty(languageFr.getFrenchWord())
        || StringUtils.isEmpty(languageFr.getEnglishMeaning())
        || StringUtils.isEmpty(languageFr.getGender())
        || languageFr.getPosTags().isEmpty()
        || StringUtils.isEmpty(languageFr.getPronunciation())) {
      throw new InvalidObjectException("one of the primary fields is empty");
    }

    String gender = sanitizeString(languageFr.getGender());
    return LanguageFrEntity.builder()
        .frenchWord(sanitizeString(languageFr.getFrenchWord()))
        .englishMeaning(sanitizeString(languageFr.getEnglishMeaning()))
        .gender(gender)
        .posTag(sanitizeString(StringUtils.join(languageFr.getPosTags(), ',')))
        .pronunciation(sanitizeString(languageFr.getPronunciation()))
        .gender2(processGender(gender, languageFr.getGender2()))
        .createTimestamp(currentTs)
        .updateTimestamp(currentTs)
        .build();
  }

  private char processGender(String gender, Character gender2) {
    return switch (gender) {
      case "le" -> 'm';
      case "la" -> 'f';
      case "uni" -> '-';
      default -> gender2 == null ? '-' : gender2;
    };
  }

  private String sanitizeString(String str) {
    return str.strip().toLowerCase();
  }

  private LanguageFrEntity generateLanguageFrEntity(LanguageFr languageFr) {
    return LanguageFrEntity.builder()
        .frenchWord(languageFr.getFrenchWord())
        .englishMeaning(languageFr.getEnglishMeaning())
        .pronunciation(languageFr.getPronunciation())
        .build();
  }
}
