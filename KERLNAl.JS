import React, { useState } from 'react';

const ADDRESS_LENGTH = 43;

// Investment Plan Component
const InvestmentPlan = ({ plan, onInvest }) => {
    const handleInvest = () => {
        onInvest(plan.name, plan.initial_investment);
    };

    return (
        <div>
            <h3>{plan.name}</h3>
            <p>Initial Investment: {plan.initial_investment}</p>
            <p>Monthly Return: {plan.monthly_return}</p>
            <p>Annual Return: {plan.annual_return}</p>
            <p>Net Annual Return: {plan.net_annual_return}</p>
            <p>Slots: {plan.slots}</p>
            <button onClick={handleInvest}>Invest</button>
        </div>
    );
};

// HollowEngine Component
const HollowEngine = () => {
    const [plans, setPlans] = useState([]);
    const [investmentMessage, setInvestmentMessage] = useState('');

    const addPlan = (planName, initialInvestment, monthlyReturn, annualReturn, netAnnualReturn, slots) => {
        const newPlan = {
            name: planName,
            initial_investment: initialInvestment,
            monthly_return: monthlyReturn,
            annual_return: annualReturn,
            net_annual_return: netAnnualReturn,
            slots: slots
        };
        setPlans((prevPlans) => [...prevPlans, newPlan]);
        console.log(`Plan added: ${planName} with initial investment ${initialInvestment}`);
    };

    const invest = (planName, amount) => {
        setInvestmentMessage(`Investment in plan ${planName}: ${amount}`);
        console.log(`Investment in plan ${planName}: ${amount}`);
    };

    const authenticateMessage = (messageHash) => {
        console.log(`Message authenticated with hash: ${messageHash}`);
    };

    // Simulated data
    const messageHash = "0xabcdef1234567890abcdef1234567890abcdef1234567890abcdef1234567890";

    return (
        <div>
            <h1>Hollow Engine Investment</h1>
            <button onClick={() => addPlan('Economic Plan', 500, 5, 60, 300, 500)}>Add Economic Plan</button>
            <div>
                {plans.map((plan, index) => (
                    <InvestmentPlan key={index} plan={plan} onInvest={invest} />
                ))}
            </div>
            <p>{investmentMessage}</p>
            <button onClick={() => authenticateMessage(messageHash)}>Authenticate Message</button>
        </div>
    );
};

// Main App Component
const App = () => {
    return (
        <div>
            <HollowEngine />
        </div>
    );
};

export default App;
