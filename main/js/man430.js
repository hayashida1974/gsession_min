 function hideExtDomainArea() {
        if (document.forms[0].man430ExtPageDspKbn[0].checked) {
            $("#extDomainArea").addClass("display_n");
        } else {
            $("#extDomainArea").removeClass("display_n");
        }
    }