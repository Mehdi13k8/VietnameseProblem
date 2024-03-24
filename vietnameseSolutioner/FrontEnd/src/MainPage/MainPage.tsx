import React, { useEffect, useState } from 'react';

import Filter from '../Filter/Filtre';
import ResultsService from '../Services/ResultsService';
import './MainPage.scss';

const MainPage = () => {
    const [results, setResults] = React.useState([]);
    const [resultsCpy, setResultsCpy] = React.useState([]);
    const [refreshPage, setRefreshPage] = React.useState(false);

    const refreshResults = () => {
        ResultsService.getResults().then((data: any) => {
            setResults(data);
            setResultsCpy(data);
        })
            .catch((error: any) => {
                console.error('Error:', "data ==>", error);
            });
    };

    React.useEffect(() => {
        refreshResults();
    }, []);

    React.useEffect(() => {
        if (refreshPage) {
            setRefreshPage(false);
        }
    }, [refreshPage]);

    const loadCalculationResult = (e : any) => {
        setResults([]);
        ResultsService.loadCalculationResult({})
            .then((data: any) => {
            })
            .catch((error: any) => {
                console.error('Error:', "data ==>", error);
            })
            .finally(() => {
                refreshResults();
            });
    };

    const lineEdit = (id: any) => {
        console.log("id ==>", id);
    }

    return (
        <>
            <header className="App-header">
            </header>

            <button style={{ backgroundColor: 'lightsteelblue', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
            onClick={loadCalculationResult}>Load Calculation Result</button>

            <br />
            <label htmlFor="filter"
            >Filter by result:</label>
            <Filter
                data={resultsCpy} onFilter={(filteredData: any) => {
                    setResults(filteredData);
                    setRefreshPage(true);
                }} />

            <table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Result</th>
                        <th>Duration</th>
                        <th>calculation</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {results.map((result: any) => (
                        <tr key={result.id}>
                            <td>
                                <a href={`http://localhost:3002/result/${result.id}`}>
                                    {result.id}
                                </a>
                            </td>
                            <td><input type="text" value={result.result} /> </td>
                            <td>{result.time + ' ns'}</td>
                            <td><input style={{ width: '100%' }} type="text" value={result.calculation} /> </td>
                            <td> <button id={result.id}

                            >edit</button> </td>
                            <td> <button>delete</button> </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
}

export default MainPage;