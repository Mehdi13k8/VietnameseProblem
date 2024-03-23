package com.skazy.vietnameseSolutioner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// import com.skazy.vietnameseSolutioner.Results;

@RestController
class ResultsController {

  private final ResultsRepository repository;
  @Autowired
  private ResultsService resultsService;

  ResultsController(ResultsRepository repository) {
    this.repository = repository;
  }

  // main page
  @GetMapping("/")
  String main() {
    return "Welcome to Vietnamese Solutioner!";
  }

  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/results")
  List<Results> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]
  
  // Single item
  @GetMapping("/results/{id}")
  Results one(@PathVariable Long id) {

    return repository.findById(id)
        .orElseThrow(() -> new Error("Results not found"));
    // new ResultsNotFoundException(id));
  }

  @DeleteMapping("/Results/{id}")
  void deleteResult(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @PostMapping("/results")
  public String newResult(@RequestBody String enteredNumbers) {
      resultsService.performCalculationAndSave(enteredNumbers);
      System.out.println("New result saved");
      return "New result saved";
  }
  // request to
}