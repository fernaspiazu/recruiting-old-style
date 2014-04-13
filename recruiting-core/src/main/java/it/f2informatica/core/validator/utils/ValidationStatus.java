package it.f2informatica.core.validator.utils;

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
