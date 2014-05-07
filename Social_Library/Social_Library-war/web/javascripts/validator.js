function formValidation()
{
    var uid = document.registration.login;
    var passid = document.registration.password;
    var cpassid = document.registration.cpassword;
    var uname = document.registration.firstName;
    var ulname = document.registration.lastName;
    var uemail = document.registration.email;
    if(userid_validation(uid,5,12))
    {
        if (loginCheck(uid)){
            if (passeq(passid,cpassid)){
                if(passid_validation(passid,7,12)){
                    if(allLetter(uname)){
                        if(allLetter(ulname)){
                            if(ValidateEmail(uemail)){

                        }
                        }
                    }
                }
            }
        }
    }

    return false;

}

function loginCheck(uid){
    var par;
    document.get('loginCheck', {login:uid}, function(responseText){
        par=responseText;
    });
    if (par.localeCompare("true")==0){
        return false;
    } else {
        return true;
    }
}

function emailCheck(uemail){

}


function passeq(p,cp)
{
    if (p.localeCompare(cp)==0){
        return true;
    }
    else {
        alert("Password do not equals!")
        return false;
    }
}

function userid_validation(uid,mx,my)
{
    var uid_len = uid.value.length;
    if (uid_len == 0 || uid_len >= my || uid_len < mx)
    {
        alert("User Id should not be empty / length be between "+mx+" to "+my);
        uid.focus();
        return false;
    }
    return true;
}
function passid_validation(passid,mx,my)
{
    var passid_len = passid.value.length;
    if (passid_len == 0 ||passid_len >= my || passid_len < mx)
    {
        alert("Password should not be empty / length be between "+mx+" to "+my);
        passid.focus();
        return false;
    }
    return true;
}
function allLetter(uname)
{
    var letters = /^[A-Za-z]+$/;
    if(uname.value.match(letters))
    {
        return true;
    }
    else
    {
        alert('Username must have alphabet characters only');
        uname.focus();
        return false;
    }
}

function ValidateEmail(uemail)
{
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(uemail.value.match(mailformat))
    {
        alert('Registration complete, check your email to confirm account!');
        document.registration.submit();
        return true;
    }
    else
    {
        alert("You have entered an invalid email address!");
        uemail.focus();
        return false;
    }
}