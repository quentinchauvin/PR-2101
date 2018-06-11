

class FeedBacks {
  private:  
    float hotend_temp;
    float pipe_temp;
    float dist;
    float gas;

  public:
    FeedBacks();
    void begin();
    void postFeedBacks(float, float, float, float);
    
    void setHotendTemp(int);
    void setPipeTemp(float);
    void setDist(float);
    void setGas(float);

    int getHotendTemp();
    float getPipeTemp();
    float getDist();
    float getGas();
};
