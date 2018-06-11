#include "FeedBacks.h"

FeedBacks::FeedBacks() {
  float hotend_temp = 210;
  float pipe_temp = 0;
  float dist = 0;
  float gas = 0;
}

void FeedBacks::postFeedBacks(float _hotend_temp,
                              float _pipe_temp,
                              float _dist,
                              float _gas) {
  hotend_temp = _hotend_temp;
  pipe_temp = _pipe_temp;
  dist = _dist;
  gas = _gas;
}

void FeedBacks::setHotendTemp(int _hotend_temp) {
  hotend_temp = _hotend_temp;
}
void FeedBacks::setPipeTemp(float _pipe_temp) {
  pipe_temp = _pipe_temp;
}
void FeedBacks::setDist(float _dist) {
  dist = _dist;
}
void FeedBacks::setGas(float _gas) {
  gas = _gas;
}

int FeedBacks::getHotendTemp() {
  return hotend_temp;
}
float FeedBacks::getPipeTemp() {
  return pipe_temp;
}
float FeedBacks::getDist() {
  return dist;
}
float FeedBacks::getGas() {
  return gas;
}
