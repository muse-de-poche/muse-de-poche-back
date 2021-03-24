package org.musedepoche.validator;

import org.musedepoche.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>Classe de validation d'un message.
 * <p>Critères:
 * <ul>
 * 	<li>Pas de texte vide</li>
 * 	<li>Pas de texte uniquement composé de {@link Character#isWhitespace(int) caractères espace}</li>
 * 	<li>Limite de 400 caractères</li>
 * </ul>
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Message
 * @see Character#isWhitespace(int)
 * @since 0.1
 **/
@Component
public class MessageValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Message.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Message message = (Message) target;

		if (message.getSender() == null) {
			errors.rejectValue("sender", "message.sender.null",
					"L'expéditeur doit être non-nul");
		}
		
		if (message.getSubject() == null) {
			errors.rejectValue("subject", "message.subject.null",
					"Le sujet doit être non-nul");
		}
		
		if (message.getText() == null) {
			errors.rejectValue("text", "message.text.null",
					"Le texte doit être non-nul");
		} else if (message.getText().isBlank()) {
			errors.rejectValue("text", "message.text.blank",
					"Le corps du message ne doit pas être vide ou contenir uniquement des espace");
		} else if (message.getText().length() > 400) {
			errors.rejectValue("text", "message.text.length.max",
					"Le corps du message est limité à 400 caractères");
		}	
	}

}
