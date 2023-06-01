import React, { useState } from 'react';
import App from './App';

function FindCareer() {
	const [currentPage, setCurrentPage] = useState('');
	
	const [careerId, setCareerId] = useState("");
	const [careerName, setCareerName] = useState("");
	const [companyName, setCompanyName] = useState("");
	const [position, setPosition] = useState("");
	const [dateStarted, setDateStarted] = useState("");

	function handleFindCareerById() {
		fetch(`http://localhost:8080/springtosalesforce/careers/${careerId}`)
			.then((response) => response.json())
			.then((career) => {
				setCareerName(career.Name);
				setCompanyName(career.Company_Name__c);
				setPosition(career.Position__c);
				setDateStarted(career.Date_Started__c);
			});
	}

	function handleCareerIdChange(event) {
		setCareerId(event.target.value);
	}
	
	const handleGoHome = () => {
		setCurrentPage('goHome');
	};
	
	const renderPage = () => {
		switch (currentPage) {
			case 'goHome':
				return <App />;
			default:
				return (
					<div>
						<button onClick={handleGoHome}>Return to Actions</button>
					</div>
				);
		}
	};

	return (
		<div className="App">
			<main>
				<section>
					<h2>Find Career By ID</h2>
					<label>
						Career ID:
						<input type="text" value={careerId} onChange={handleCareerIdChange} />
					</label>
					<button onClick={handleFindCareerById}>Find</button>
					<p>
						Career Name: {careerName}
						<br />
						Company Name: {companyName}
						<br />
						Position: {position}
						<br />
						Date Started: {dateStarted}
					</p>
				</section>{renderPage()}
			</main>
		</div>
	);
}

export default FindCareer;