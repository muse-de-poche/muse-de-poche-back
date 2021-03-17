package org.musedepoche.validator;

import org.musedepoche.model.Message;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>Classe de validation d'un message.
 * <p>Critères:
 * <ul>
 * 	<li>Pas de texte vide</li>
 * 	<li>Pas de texte uniquement composé de {@link Character#isWhitespace(int) caractères espace}</li>
 * </ul>
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Message
 * @see Character#isWhitespace(int)
 * @since 0.1
 */
public class MessageValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Message.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Message message = (Message) target;

		if (message.getText().isBlank()) {
			errors.rejectValue("text", "message.text.blank",
					"Le corps du message ne doit pas être vide ou contenir uniquement des espace");
		}
		
		if (message.getText().length() > 400) {
			errors.rejectValue("text", "message.text.length.max",
					"Le corps du message est limité à 400 caractères");
		}
		
	}

}
