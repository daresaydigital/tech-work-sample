export class WeatherData {
    constructor(
        public code: number,
        public city: string,
        public lon: number,
        public lat: number,
        public weatherDescription: string,
        public weatherCondition: string,
        public temp: string,
        public wind: string,
        public humidity: number,   
    ){}
}