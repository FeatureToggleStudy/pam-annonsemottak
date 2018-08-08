package no.nav.pam.annonsemottak.annonsemottak.amedia.filter;

enum EmployerBlacklist {
    BRUK_TIL_TEST("testemployerblacklisted");

    private final String companyname;

    EmployerBlacklist(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyname() {
        return companyname;
    }
}
