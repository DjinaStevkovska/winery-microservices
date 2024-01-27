package io.pivotal.microservices.services.web;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class SearchCriteria {
	private String wineryName;

	private String wineryOwner;

	public String getWineryName() {
		return wineryName;
	}

	public void setWineryName(String wineryName) {
		this.wineryName = wineryName;
	}

	public String getWineryOwner() {
		return wineryOwner;
	}

	public void setWineryOwner(String wineryOwner) {
		this.wineryOwner = wineryOwner;
	}

	public boolean isValid() {
		if (StringUtils.hasText(wineryName))
			return !(StringUtils.hasText(wineryOwner));
		else
			return (StringUtils.hasText(wineryOwner));
	}

	public boolean validate(Errors errors) {
		if (StringUtils.hasText(wineryName)) {
			if (wineryName.length() < 3)
				errors.rejectValue("wineryName", "badFormat",
						"Winery name should be at least 3 letters");

			if (StringUtils.hasText(wineryOwner)) {
				errors.rejectValue("wineryOwner", "nonEmpty",
						"Cannot specify winery name and search text");
			}
		} else if (StringUtils.hasText(wineryOwner)) {
			; // Nothing to do
		} else {
			errors.rejectValue("wineryName", "nonEmpty",
					"Must specify either an winery name or search text");

		}

		return errors.hasErrors();
	}

	@Override
	public String toString() {
		return (StringUtils.hasText(wineryName) ? "name: " + wineryName
				: "")
				+ (StringUtils.hasText(wineryOwner) ? " text: " + wineryOwner
						: "");
	}
}
