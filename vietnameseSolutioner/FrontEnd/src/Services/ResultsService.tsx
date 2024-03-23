import axios from 'axios';

class ResultService {
    async getResults() {
        try {
            // axios http://127.0.0.1:8080/results
            // cors allow all
            const response = await axios.get('http://localhost:8080/results');
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }

    // create Result
    async loadCalculationResult(data: any) {
        try {
            const response = await axios.post('http://127.0.0.1:8080/results', {});
            return response.data;
        } catch (error) {
            console.error('Error:', error);
            throw error;
        }
    }
}

export default new ResultService();