import axios from 'axios';
import React, { useEffect, useState } from 'react';

const Result = () => {
    const [result, setResult] = useState({
        id: '',
        result: '',
        time: '',
        calculation: '',
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/results/' + window.location.pathname.split('/').pop());
                const data = await response?.data;
                setResult(data);
                console.log('Result:', data);
            } catch (error) {
                console.error('Error fetching result:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <div>
            <button onClick={() => window.history.back()}>Back</button>
            <h1>Result Page</h1>
            <div>
                <h2>Result</h2>
                {
                    result &&
                    <div key={result.id}>
                        <p>{result.id}</p>
                        <p>{result.result}</p>
                        <p>{result.time + ' ns'}</p>
                        <p>{result.calculation}</p>
                    </div>
                }
            </div>
        </div>
    );
};

export default Result;
