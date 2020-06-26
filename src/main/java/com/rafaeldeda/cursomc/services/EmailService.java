package com.rafaeldeda.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.rafaeldeda.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmation(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
