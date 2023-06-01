import React, { useState, useEffect } from 'react';

function GetCareer(props) {
  const [career, setCareer] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/springtosalesforce/careers/a08Dn000002EK0TIAW`)
      .then(response => response.json())
      .then(data => setCareer(data))
      .catch(error => console.error(error));
  }, [props.sfId]);

  if (!career) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h2>{career.Name}</h2>
      <p>Company: {career.Company_Name__c}</p>
      <p>Position: {career.Position__c}</p>
      <p>Date Started: {career.Date_Started__c}</p>
    </div>
  );
}

export default GetCareer;
