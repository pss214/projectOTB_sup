export interface BusStop {
  [x: string]: any;
  place: string;
  lat: number;
  lng: number;
}

const BUS_STOP: BusStop[] = [
  { place: "건대입구역", lat: 37.539922, lng: 127.070609 },
  { place: "어린이대공원역", lat: 37.547263, lng: 127.074181 },
];

export default BUS_STOP;