package com.vv.personal.twm.language.fr.remote;

import com.vv.personal.twm.language.fr.model.LanguageFr;
import com.vv.personal.twm.language.fr.service.LanguageFrService;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Vivek
 * @since 2025-05-17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("language/fr")
public class LanguageFrController {

  private final LanguageFrService languageFrService;

  @PostMapping("/word")
  public ResponseEntity<String> addWord(@RequestBody LanguageFr frenchWordDetail) {
    try {
      languageFrService.addWord(frenchWordDetail);
      log.info("added word {}", frenchWordDetail.getFrenchWord());
      return ResponseEntity.ok().build();
    } catch (InvalidObjectException e) {
      log.error("invalid object exception while adding word", e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      log.error("Error while adding word", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/word/{word}")
  public ResponseEntity<LanguageFr> getWord(@PathVariable String word) {
    try {
      Optional<LanguageFr> languageFr = languageFrService.getFrenchWordDetail(word);
      if (languageFr.isEmpty()) {
        log.info("Did not find french meaning of {}", word);
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(languageFr.get());
    } /*catch (DataAccessException e) {
        log.error("Internal server error while getting french meaning of {}", word, e);
        return ResponseEntity.internalServerError().build();
      }*/ catch (Exception e) {
      log.error("Error while getting french meaning of {}", word, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/word")
  public ResponseEntity<Void> updateWord(@RequestBody LanguageFr frenchWordDetail) {
    try {
      languageFrService.updateWord(frenchWordDetail);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("Error while updating french meaning of {}", frenchWordDetail, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/word/exist/{word}")
  public ResponseEntity<Boolean> doesWordExist(@PathVariable String word) {
    try {
      boolean doesWordExist = languageFrService.doesWordExist(word);
      return ResponseEntity.ok(doesWordExist);
    } catch (Exception e) {
      log.error("Error while checking if {} exists", word, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/words")
  public ResponseEntity<List<LanguageFr>> getAllWords() {
    try {
      List<LanguageFr> allFrenchWords = languageFrService.getAllFrenchWords();
      log.info("Retrieved {} french words", allFrenchWords.size());
      return ResponseEntity.ok(allFrenchWords);
    } catch (Exception e) {
      log.error("Failed to retrieve all french words", e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/words/meaningHas")
  public ResponseEntity<List<LanguageFr>> getWordsContainingWord(@RequestParam String word) {
    try {
      List<LanguageFr> matchingFrenchWords = languageFrService.getFrenchWordsContainingWord(word);
      log.info(
          "Retrieved {} french words having en meaning containing {}",
          matchingFrenchWords.size(),
          word);
      return ResponseEntity.ok(matchingFrenchWords);
    } catch (Exception e) {
      log.error("Failed to retrieve french words having meaning containing {}", word, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/words/pos")
  public ResponseEntity<List<LanguageFr>> getWordsMatchingPos(@RequestParam String posTag) {
    try {
      List<LanguageFr> matchingFrenchWords = languageFrService.getFrenchWordsMatchingPos(posTag);
      log.info("Retrieved {} french words matching pos {}", matchingFrenchWords.size(), posTag);
      return ResponseEntity.ok(matchingFrenchWords);
    } catch (Exception e) {
      log.error("Failed to retrieve french words matching postag {}", posTag, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/words/gender")
  public ResponseEntity<List<LanguageFr>> getWordsMatchingGender(@RequestParam String gender) {
    try {
      List<LanguageFr> matchingFrenchWords = languageFrService.getFrenchWordsMatchingGender(gender);
      log.info("Retrieved {} french words matching gender {}", matchingFrenchWords.size(), gender);
      return ResponseEntity.ok(matchingFrenchWords);
    } catch (Exception e) {
      log.error("Failed to retrieve french words matching gender {}", gender, e);
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/bulk-upload/local/")
  public ResponseEntity<Integer> bulkUpload(
      @RequestParam String fileSource, @RequestParam String source) {
    try {
      int wordsUploaded = languageFrService.bulkUpload(fileSource, source);
      log.info("Completed bulk upload of {} word records", wordsUploaded);
      return ResponseEntity.ok(wordsUploaded);
    } catch (Exception e) {
      log.error("Error while bulk upload", e);
      return ResponseEntity.badRequest().build();
    }
  }
}
