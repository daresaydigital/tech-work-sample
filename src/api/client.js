const client = async (url) => {
    try {
      const response = await fetch(url, {
        headers: {
            'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWFiNzY0ZGEwNWQyNWUyNmIxM2M4NDU1Nzg1NzIxNyIsInN1YiI6IjYzMzhhMWRiOTYwY2RlMDA4MjBhZjY5OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dr6ZsoYUhfDSmDnHNbe4w_gwb4OL_6PjA9bmUHK9GPM",
            'Content-Type': "application/json;charset=utf-8"
        }
      });
      if (!response.ok) {
        throw new Error(
          `This is an HTTP error: The status is ${response.status}`
        );
      }
      const actualData = await response.json();
      return actualData;
    } catch (error) {
      throw new Error(`There was an error with fetching data: ${error.message}`);
    }
  };
  
  export default client;