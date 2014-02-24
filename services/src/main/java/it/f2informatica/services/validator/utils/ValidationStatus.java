package it.f2informatica.services.validator.utils;

public enum ValidationStatus {

	SUCCESS {
		@Override
		public String toString() {
			return "SUCCESS";
		}
	},

	FAIL {
		@Override
		public String toString() {
			return "FAIL";
		}
	}

}
