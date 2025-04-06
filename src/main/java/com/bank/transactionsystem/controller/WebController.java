package com.bank.transactionsystem.controller;

import com.bank.transactionsystem.model.Transaction;
import com.bank.transactionsystem.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final TransactionRepository repository;

    @GetMapping("/")
    public String listTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        model.addAttribute("transactions", repository.findAll(page, size));
        model.addAttribute("newTransaction", new Transaction());
        return "transactions";
    }

    @PostMapping("/transactions")
    public String createTransaction(@ModelAttribute Transaction transaction) {
        repository.save(transaction);
        return "redirect:/";
    }
}