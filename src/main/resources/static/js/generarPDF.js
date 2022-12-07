function createPDF(license){
    const licenseHTML = document.getElementById("license-container");
    setValues(license);

    var opt = {
      margin:       1,
      filename:     'license.pdf', //VER NOMBRE
      image:        { type: 'jpeg', quality: 0.98 },
      html2canvas:  { scale: 2 },
      jsPDF:        { unit: 'in', format: 'letter', orientation: 'portrait' }
    };
    html2pdf().set(opt).from(licenseHTML).toPdf().output('blob').then((data) => {
        let fileURL = URL.createObjectURL(data);
        document.getElementById("license-pdf").src = fileURL;
    })
}

function setValues(license){
    let type = document.getElementById("license-type");
    type.innerHTML = license.type.name;
    let blood = document.getElementById("license-blood");
    let factor = license.licenseHolder.rhFactor;
    if(factor == "NEGATIVO"){
        blood.innerHTML = license.licenseHolder.bloodType + "-";
    } else if(factor=="POSITIVO"){
        blood.innerHTML = license.licenseHolder.bloodType + "+";
    } else {
        blood.innerHTML = license.licenseHolder.bloodType;
    }
    let donor = document.getElementById("license-donor");
    donor.innerHTML = license.licenseHolder.organDonor ? "SI":"NO";
    let typeId = document.getElementById("license-typeid");
    typeId.innerHTML = license.licenseHolder.type;
    let identification = document.getElementById("license-identification");
    identification.innerHTML = license.licenseHolder.identification;
    let surname = document.getElementById("license-surname");
    surname.innerHTML = license.licenseHolder.surname;
    let name = document.getElementById("license-name");
    name.innerHTML = license.licenseHolder.name;
    let address = document.getElementById("license-address");
    let addressInfo = license.licenseHolder.address;
    let addressString = `${addressInfo.street} ${addressInfo.number}`;
    if(addressInfo.department != ""){
        addressString += ` Depto.${addressInfo.department}`;
    }
    if(addressInfo.floor != ""){
        addressString += ` Piso ${addressInfo.floor}`;
    }
    address.innerHTML = `${addressString} - ${addressInfo.locality}`;
    let birth = document.getElementById("license-birth");
    birth.innerHTML = license.licenseHolder.birthDate;
    let issue = document.getElementById("license-issue");
    issue.innerHTML = license.fromDate;
    let expiration = document.getElementById("license-expires");
    expiration.innerHTML = license.expirationDate;
    let comments = document.getElementById("license-comments");
    comments.innerHTML = license.comments;
}