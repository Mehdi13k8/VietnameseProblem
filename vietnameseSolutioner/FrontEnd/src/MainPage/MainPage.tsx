import React, { useEffect, useState } from 'react';

import Filter from '../Filter/Filtre';
import ResultsService from '../Services/ResultsService';
import './MainPage.scss';
import axios from 'axios';

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

    const loadCalculationResult = (e: any) => {
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

    // delete result by id
    const deleteResult = (id: any) => {
        console.log("id ==>", id);
        axios.delete(`http://localhost:8080/result/${id}`)
            .then((response: any) => {
                console.log("response ==>", response);
                refreshResults();
            })
            .catch((error: any) => {
                alert("Error: Deleting result failed");
                console.error('Error:', "data ==>", error);
            });
    }

    // delete All the results
    const deleteAllResults = () => {
        axios.delete(`http://localhost:8080/results`)
            .then((response: any) => {
                console.log("response ==>", response);
                refreshResults();
            })
            .catch((error: any) => {
                alert("Error: Deleting result failed");
                console.error('Error:', "data ==>", error);
            });
    }

    const lineCheck = (id: any) => {
        console.log("id ==>", id);
    }

    return (
        <>
            <header className="App-header">
            </header>

            <button style={{ backgroundColor: 'lightsteelblue', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                onClick={loadCalculationResult}> (Re)Load Calculation Result</button>
            <button style={{ backgroundColor: 'red', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                onClick={deleteAllResults}>Delete All Results</button>

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
                            <td><input onChange={(e) => {
                                // only allow numbers
                                let resultsCpy: any = [...results];
                                let index = resultsCpy.findIndex((res: any) => res.id == result.id);
                                resultsCpy[index].result = e.target.value;
                                setResults(resultsCpy);

                                // update result
                                axios.put(`http://localhost:8080/result/${result.id}`, {
                                    result: e.target.value,
                                    time: result.time,
                                    calculation: result.calculation,
                                    duration: result.duration
                                })
                                    .then((response: any) => {
                                        console.log("response ==>", response);
                                        refreshResults();
                                    })
                                    .catch((error: any) => {
                                        alert("Error: Updating result failed");
                                        console.error('Error:', "data ==>", error);
                                    });
                            }}
                                type="text" value={result.result} /> </td>
                            <td style={{ width: '9vw', textAlignLast: 'center' }}>
                                <input disabled type="text" value={result.time + ' ns'}
                                />
                            </td>
                            <td style={{ textAlignLast: 'center' }}
                            ><input style={{ width: '100%' }} type="text" value={result.calculation} /> </td>
                            <td
                                style={{ width: '0' }}
                            > <button id={result.id}
                                onClick={() => lineCheck(result.id)}
                                style={{ backgroundColor: 'lightblue', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                            >Check</button> </td>
                            <td
                                style={{ width: '0' }}
                            > <button id={result.id}
                                onClick={() => deleteResult(result.id)}
                                style={{ backgroundColor: 'red', color: 'white', padding: '14px 20px', margin: '8px 0', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                            >delete</button> </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
}

export default MainPage;