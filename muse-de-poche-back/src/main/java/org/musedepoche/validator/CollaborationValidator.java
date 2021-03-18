package org.musedepoche.validator;

import org.musedepoche.model.Collaboration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * <p>
 * Classe de validation d'une demande de collaboration.
 * <p>
 * Critères:
 * <ul>
 * 	<li>Pas de valeur nulle pour la compostion</li>
 * 	<li>Pas de valeur nulle pour le compositeur</li>
 * 	<li>Pas de texte vide</li>
 * 	<li>Pas de texte uniquement composé de {@link Character#isWhitespace(int) caractères espace}</li>
 * 	<li>Limite de 400 caractères</li>
 * </ul>
 * 
 * @author Cyril R.
 * @see org.musedepoche.model.Collaboration
 * @see Character#isWhitespace(int)
 * @since 0.1
 */
@Component
public class CollaborationValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Collaboration.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Collaboration collaboration = (Collaboration) target;

		if (collaboration.getComposition() == null) {
			errors.rejectValue("composition", "collaboration.composition.null",
					"La composition doit être non-nulle");
		}

		if (collaboration.getComposer() == null) {
			errors.rejectValue("composer", "collaboration.composer.null",
					"Le compositeur doit être non-nul");
		}
				
		if (collaboration.getText() == null) {
			errors.rejectValue("text", "collaboration.text.null",
					"Le texte doit être non-nul");
		} else if (collaboration.getText().isBlank()) {
			errors.rejectValue("text", "collaboration.text.blank",
					"Le corps du texte ne doit pas être vide ou contenir uniquement des espace");
		} else if (collaboration.getText().length() > 400) {
			errors.rejectValue("text", "collaboration.text.length.max",
					"Le corps du texte est limité à 400 caractères");
		}
	}

}
